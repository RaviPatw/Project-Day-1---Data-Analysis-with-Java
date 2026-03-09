// ========================
// Bar color palette
// ========================
const COLORS = [
    'linear-gradient(180deg, #00ffe0, #00b8a0)',
    'linear-gradient(180deg, #ff3f6c, #b02848)',
    'linear-gradient(180deg, #ffe040, #b89e00)',
    'linear-gradient(180deg, #a78bfa, #6d28d9)',
    'linear-gradient(180deg, #34d399, #059669)',
    'linear-gradient(180deg, #f97316, #c2410c)',
    'linear-gradient(180deg, #60a5fa, #2563eb)',
    'linear-gradient(180deg, #f472b6, #be185d)',
];

// ========================
// Demo data (used if Java server is not running)
// ========================
const DEMO_BIRDS = [
    {name:'Owl',value:8},{name:'Eagle',value:5},{name:'Hawk',value:6},
    {name:'Falcon',value:4},{name:'Heron',value:3},{name:'Sparrow',value:7},
    {name:'Duck',value:5},{name:'Penguin',value:2}
];
const DEMO_STATUS = [
    {name:'Critically Endangered',value:1},{name:'Endangered',value:4},
    {name:'Vulnerable',value:6},{name:'Near Threatened',value:8},{name:'Least Concern',value:22}
];
const DEMO_DIET = [
    {name:'fish',value:12},{name:'seeds',value:8},{name:'insects',value:7},
    {name:'frogs',value:5},{name:'squid',value:4},{name:'berries',value:3}
];

// ========================
// Fetch all data from Java server
// ========================
async function loadData() {
    setLoading('barChart');
    setLoading('statusChart');
    setLoading('dietChart');
    document.getElementById('lineCard').style.display = 'none';
    document.getElementById('statsRow').style.display = 'none';

    let birds, status, diet, statsData;

    try {
        const [bRes, sRes, dRes, stRes] = await Promise.all([
            fetch('http://localhost:8080/data'),
            fetch('http://localhost:8080/status'),
            fetch('http://localhost:8080/diet'),
            fetch('http://localhost:8080/stats'),
        ]);
        if (!bRes.ok) throw new Error('HTTP ' + bRes.status);
        [birds, status, diet, statsData] = await Promise.all([
            bRes.json(), sRes.json(), dRes.json(), stRes.json()
        ]);
    } catch (e) {
        document.getElementById('barChart').innerHTML =
            `<p class="status error">&#9888; Could not reach Java server &mdash; showing demo data</p>`;
        await new Promise(r => setTimeout(r, 700));
        birds     = DEMO_BIRDS;
        status    = DEMO_STATUS;
        diet      = DEMO_DIET;
        statsData = { largestGroup: 'Owl( 8 birds )' };
    }

    renderBarChart('barChart',    birds);
    renderLineChart(birds);
    renderBarChart('statusChart', status);
    renderBarChart('dietChart',   diet);
    renderStats(birds, status, diet, statsData);
}

function setLoading(id) {
    document.getElementById(id).innerHTML = '<p class="status">Fetching from Java server...</p>';
}

// ========================
// Bar Chart (reusable — pass container ID + data)
// ========================
function renderBarChart(containerId, data) {
    const max        = Math.max(...data.map(d => d.value));
    const GRID_STEPS = 5;
    const stepVal    = Math.ceil(max / GRID_STEPS) || 1;
    const chartMax   = stepVal * GRID_STEPS;

    let gridHTML = '<div class="gridlines">';
    for (let i = GRID_STEPS; i >= 0; i--) {
        gridHTML += `<div class="gridline"><span>${i * stepVal}</span></div>`;
    }
    gridHTML += '</div>';

    let barsHTML = '';
    data.forEach((d, i) => {
        const pct   = (d.value / chartMax) * 100;
        const delay = (i * 0.07).toFixed(2);
        barsHTML += `
      <div class="bar-group">
        <div class="bar"
          data-value="${d.value}"
          style="height:${pct}%;background:${COLORS[i % COLORS.length]};animation-delay:${delay}s">
        </div>
        <div class="bar-label">${d.name}</div>
      </div>`;
    });

    document.getElementById(containerId).innerHTML = `
    <div class="chart-area">${gridHTML}${barsHTML}</div>
    <div class="x-axis"></div>
  `;
}

// ========================
// Line Chart (SVG) — bird type groups
// ========================
function renderLineChart(data) {
    document.getElementById('lineCard').style.display = 'block';
    const svg   = document.getElementById('lineChart');
    const W     = 760, H = 200, PAD = 20;
    const max   = Math.max(...data.map(d => d.value)) * 1.1;
    const count = data.length;

    const xs = data.map((_, i) => PAD + (i / (count - 1)) * (W - PAD * 2));
    const ys = data.map(d => H - PAD - (d.value / max) * (H - PAD * 2));

    const pathD = xs.map((x, i) => `${i === 0 ? 'M' : 'L'} ${x} ${ys[i]}`).join(' ');
    const areaD = pathD + ` L ${xs[count - 1]} ${H} L ${xs[0]} ${H} Z`;

    const dots = data.map((d, i) =>
        `<circle class="dot" cx="${xs[i]}" cy="${ys[i]}" r="5"><title>${d.name}: ${d.value}</title></circle>`
    ).join('');

    svg.innerHTML = `
    <defs>
      <linearGradient id="areaGrad" x1="0" y1="0" x2="0" y2="1">
        <stop offset="0%"   stop-color="#ff3f6c"/>
        <stop offset="100%" stop-color="transparent"/>
      </linearGradient>
    </defs>
    <path class="area-path" d="${areaD}"/>
    <path class="line-path"  d="${pathD}"/>
    ${dots}
  `;
}

// ========================
// Stats Row — summary across all datasets
// ========================
function renderStats(birds, status, diet, statsData) {
    const totalBirds = birds.reduce((a, b) => a + b.value, 0);

    const endangered = (status.find(s => s.name === 'Endangered') || {value: 0}).value;
    const critical   = (status.find(s => s.name === 'Critically Endangered') || {value: 0}).value;
    const leastConcern = (status.find(s => s.name === 'Least Concern') || {value: 0}).value;

    const topDiet = diet.length
        ? diet.reduce((a, b) => a.value > b.value ? a : b)
        : {name: '-', value: 0};

    const largestGroup = statsData.largestGroup || '-';

    const items = [
        { label: 'Total Birds',        value: totalBirds,            color: '#00ffe0' },
        { label: 'Largest Type Group', value: largestGroup,          color: '#ff3f6c' },
        { label: 'Endangered',         value: endangered + critical, color: '#ffe040' },
        { label: 'Least Concern',      value: leastConcern,          color: '#34d399' },
        { label: 'Top Diet',           value: topDiet.name,          color: '#a78bfa' },
        { label: 'Diet Varieties',     value: diet.length,           color: '#60a5fa' },
    ];

    document.getElementById('stats').innerHTML = items.map(s => `
    <div class="stat">
      <div class="stat-label">${s.label}</div>
      <div class="stat-value" style="color:${s.color}">${s.value}</div>
    </div>
  `).join('');

    document.getElementById('statsRow').style.display = 'block';
}

// Auto-run on page load
loadData();
