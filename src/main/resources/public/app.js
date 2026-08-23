document.getElementById('generatorForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const payload = {
        length: parseInt(document.getElementById('length').value),
        useUpper: document.getElementById('useUpper').checked,
        useLower: document.getElementById('useLower').checked,
        useNumbers: document.getElementById('useNumbers').checked,
        useSymbols: document.getElementById('useSymbols').checked
    };

    const errorMessage = document.getElementById('errorMessage');
    const resultContainer = document.getElementById('resultContainer');
    errorMessage.innerText = '';

    try {
        const response = await fetch('/api/generate', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        const data = await response.json();

        if (!response.ok) {
            errorMessage.innerText = data.message || 'Error generating password';
            resultContainer.style.display = 'none';
            return;
        }

        document.getElementById('passwordOutput').innerText = data.password;
        document.getElementById('entropyOutput').innerText = data.entropy.toFixed(2);

        const ratingBadge = document.getElementById('ratingOutput');
        ratingBadge.innerText = data.rating;
        ratingBadge.className = `badge badge-${data.rating}`;

        resultContainer.style.display = 'block';
    } catch (err) {
        errorMessage.innerText = 'Server error. Ensure the backend app is running.';
    }
});