var phoneCounter = 1;

function createInputPhoneNumber() {
    var input = document.createElement("input");
    input.type = "text";
    input.title = "Enter phone number (up to 12 digits)";
    input.className = "form-control";
    input.name = "phones";
    input.placeholder = "Enter phone number (up to 12 digits)";

    var deleteButton = document.createElement("button");
    deleteButton.className = "btn btn-outline-danger btn-sm mt-1";
    deleteButton.id = "deleteButton" + phoneCounter;
    deleteButton.textContent = "Remove";
    deleteButton.onclick = function () {
        deleteInputField(deleteButton);
    };

    var rowDiv = document.createElement("div");
    rowDiv.className = "row mt-2 d-flex justify-content-center";

    var inputDiv = document.createElement("div");
    inputDiv.className = "col-8";
    inputDiv.appendChild(input);

    var deleteButtonDiv = document.createElement("div");
    deleteButtonDiv.className = "col-4";
    deleteButtonDiv.appendChild(deleteButton);

    rowDiv.appendChild(inputDiv);
    rowDiv.appendChild(deleteButtonDiv);

    document.getElementById("phones").appendChild(rowDiv);

    phoneCounter++;

    input.addEventListener('input', function () {
        validatePhoneNumber(input);
    });
}

function deleteInputField(deleteButton) {
    var rowDiv = deleteButton.parentElement.parentElement;
    rowDiv.remove();
    phoneCounter--;
}

function validatePhoneNumber(input) {
    var cleanedValue = input.value.replace(/\D/g, '');

    if (cleanedValue.length > 12) {
        cleanedValue = cleanedValue.slice(0, 12);
    }
    input.value = cleanedValue;
}


var noteCounter = 1;

function createTextarea() {
    var textarea = document.createElement("textarea");

    textarea.className = "form-control mt-2";
    textarea.id = "note" + noteCounter;
    textarea.name = "notes";
    textarea.rows = 4;
    textarea.placeholder = "Put your note here...";

    var deleteButton = document.createElement("button");
    deleteButton.className = "btn btn-outline-danger btn-sm mt-2";
    deleteButton.id = "deleteButton" + noteCounter;
    deleteButton.textContent = "Remove";
    deleteButton.onclick = function () {
        deleteTextarea(textarea.id);
    };

    var noteDiv = document.createElement("div");
    noteDiv.className = "row mb-3";

    var noteTextArea = document.createElement("div");
    noteTextArea.className = "col-8";
    noteTextArea.appendChild(textarea);

    var noteTextAreaDelete = document.createElement("div");
    noteTextAreaDelete.className = "col-4";
    noteTextAreaDelete.appendChild(deleteButton);

    noteDiv.appendChild(noteTextArea);
    noteDiv.appendChild(noteTextAreaDelete);

    noteCounter++;

    var notesContainer = document.getElementById("notes");
    notesContainer.appendChild(noteDiv);
}

function deleteTextarea(textareaId) {
    var textarea = document.getElementById(textareaId);

    if (textarea) {
        var noteDiv = textarea.parentNode.parentNode;
        noteDiv.parentNode.removeChild(noteDiv);
    }
    noteCounter--;
}

function editItem(itemIndex, type) {

    var itemElement = document.getElementById(`${type}${itemIndex}`);
    itemElement.removeAttribute('readonly');

    if (type === 'Phone') {
        itemElement.addEventListener('input', function () {
            validatePhoneNumber(itemElement);
        });
    }

    var editButton = document.getElementById(`edit${type}Button${itemIndex}`);
    editButton.textContent = 'Save';
    editButton.onclick = function () {
        saveEditedItem(itemIndex, type);
    };

}

function saveEditedItem(itemIndex, type) {
    var itemElement = document.getElementById(`${type}${itemIndex}`);
    itemElement.setAttribute('readonly', 'true');

    var editButton = document.getElementById(`edit${type}Button${itemIndex}`);
    editButton.innerHTML = `Edit <span class="bi bi-pencil"></span>`;
    editButton.onclick = function () {
        editItem(itemIndex, type);
    };
}

function deleteItem(itemIndex, type) {
    var itemElement = document.getElementById(`${type}${itemIndex}`);
    itemElement.parentNode.parentNode.remove();
}
