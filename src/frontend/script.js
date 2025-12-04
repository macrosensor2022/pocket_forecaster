const loader = document.getElementById("loader");

document.getElementById("searchBtn").addEventListener("click", function () {
    const minBudget = document.getElementById("minBudget").value || 0;
    const maxBudget = document.getElementById("maxBudget").value || 100000;
    const os = document.getElementById("os").value || "No Preference";
    const usageChoice = document.getElementById("usage").value || 1;
    const usageText = ["Casual","Entertainment","Gaming","Photography","Productivity"][usageChoice-1];

    const url = `http://localhost:4567/api/recommendations?minBudget=${minBudget}&maxBudget=${maxBudget}&os=${os}&usageChoice=${usageChoice}`;

    loader.style.display = "block";
    clearPanels();

    fetch(url)
        .then(response => {
            if (!response.ok) throw new Error("Network response was not OK");
            return response.json();
        })
        .then(data => {
            loader.style.display = "none";
            displayResults(data, usageText);
        })
        .catch(err => {
            loader.style.display = "none";
            console.error("ERROR:", err);
            alert("Failed to fetch recommendations.");
        });
});

function clearPanels() {
    document.getElementById("mainPhones").innerHTML = "";
    document.getElementById("additionalPhones").innerHTML = "";
    document.getElementById("bonusPhones").innerHTML = "";
}

// Accordion functionality
document.querySelectorAll(".accordion").forEach(btn => {
    btn.addEventListener("click", () => {
        btn.classList.toggle("active");
        const panel = btn.nextElementSibling;
        panel.style.display = (panel.style.display === "block") ? "none" : "block";
    });
});

// Convert sentiment to label text
function getSentimentText(score) {
    if (!score && score !== 0) return "";
    if (score >= 70) return "<span style='color:green;font-weight:bold;'>Positive</span>";
    if (score >= 40) return "<span style='color:orange;font-weight:bold;'>Neutral</span>";
    return "<span style='color:red;font-weight:bold;'>Negative</span>";
}

function displayResults(data, usage) {
    const mainDiv = document.getElementById("mainPhones");
    const additionalDiv = document.getElementById("additionalPhones");
    const bonusDiv = document.getElementById("bonusPhones");

    const createPhoneCard = (p) => {
        const div = document.createElement("div");
        div.className = "phone";
        div.innerHTML = `
            <h3>${p.name}</h3>
            <p>₹${p.price}</p>
            <p>"${p.reviews && p.reviews.length ? p.reviews[0] : ""}"</p>
            <p>Sentiment: ${p.sentiment || "N/A"}% ${getSentimentText(p.sentiment)}</p>
            ${p.purchaseLink ? `<a href="${p.purchaseLink}" target="_blank" class="buy-btn">Buy →</a>` : ""}
        `;
        return div;
    };

    data.main?.forEach(p => mainDiv.appendChild(createPhoneCard(p)));
    data.additional?.forEach(p => additionalDiv.appendChild(createPhoneCard(p)));

    if (data.bonus?.length) {
        const ul = document.createElement("ul");
        ul.className = `bonus-list ${usage.toLowerCase()}`;
        data.bonus.forEach(item => {
            const li = document.createElement("li");
            li.textContent = item;
            ul.appendChild(li);
        });
        bonusDiv.appendChild(ul);
    }
}

// About Modal JS
const aboutModal = document.getElementById("aboutModal");
const aboutBtn = document.getElementById("aboutBtn");
const closeBtn = document.querySelector(".close-btn");

// Open modal
aboutBtn.onclick = () => {
    aboutModal.style.display = "block";
}

// Close modal
closeBtn.onclick = () => {
    aboutModal.style.display = "none";
}

// Close modal when clicking outside
window.onclick = (event) => {
    if (event.target === aboutModal) {
        aboutModal.style.display = "none";
    }
}
