// src/main/resources/static/validation.js

function validateNumbers(textarea) {
    var content = textarea.value;
    textarea.value = content.replace(/[^0-9\n]/g, '');
}
