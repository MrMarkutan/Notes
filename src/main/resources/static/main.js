// src/main/resources/static/main.js

function validateNumbers(textarea) {
    var content = textarea.value;
    textarea.value = content.replace(/[^0-9\n]/g, '');
}

var noteCounter = 1;

function createTextarea() {
    // Create a new textarea element
    var textarea = document.createElement("textarea");
    textarea.className = "form-control mt-2";
    textarea.id = "note" + noteCounter;
    textarea.name = "notes";
    textarea.placeholder = "Put your note here...";

    // Create a new button to delete the textarea
    var deleteButton = document.createElement("button");
    deleteButton.className = "btn btn-outline-danger btn-sm mt-2 mx-2";
    deleteButton.id = "deleteButton" + noteCounter; // Unique ID for each delete button
    deleteButton.textContent = "Delete";
    deleteButton.onclick = function () {
        deleteTextarea(textarea.id);
    };

    // Create a new div to contain the textarea and delete button
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

    // Increment the counter for the next textarea
    noteCounter++;

    // Find the container element and append the new div
    var notesContainer = document.getElementById("notes");
    notesContainer.appendChild(noteDiv);
}

function deleteTextarea(textareaId) {
    // Find the textarea element and delete button
    var textarea = document.getElementById(textareaId);

    if (textarea) {
        var noteDiv = textarea.parentNode.parentNode;
        noteDiv.parentNode.removeChild(noteDiv);
    }
    noteCounter--;
}


var editedNoteIndex;

function editNote(noteIndex) {
    editedNoteIndex = noteIndex;
    var noteElement = document.getElementById('note' + noteIndex);
    noteElement.removeAttribute('readonly');

    // var originalNote = noteElement.textContent.trim();

    // Create a new textarea element
    // var textarea = document.createElement("textarea");
    // textarea.className = "form-control";
    // textarea.value = originalNote;
    // textarea.id = 'editedNoteTextarea';  // Set a unique ID for the textarea

    // Replace the original note content with the textarea
    // noteElement.innerHTML = '';
    // noteElement.appendChild(textarea);

    // Change the "Edit" button to "Save"
    var editButton = document.getElementById('editButton' + noteIndex);
    editButton.textContent = 'Save';
    editButton.onclick = function () {
        saveEditedNote();
    };
}

function saveEditedNote() {
    var noteElement = document.getElementById('note' + editedNoteIndex);
    noteElement.setAttribute('readonly', 'true');



    // Get the edited note content
    // var editedNote = document.getElementById('editedNoteTextarea').value;

    // Update the DOM with the edited note content
    // var noteElement = document.getElementById('note' + editedNoteIndex);
    // noteElement.id = 'note' + editedNoteIndex;
    // noteElement.textContent = editedNote;

    // You can also send the edited note to the server using an AJAX request

    // Change the "Save" button back to "Edit"
    var editButton = document.getElementById('editButton' + editedNoteIndex);
    editButton.innerHTML = 'Edit <span class="bi bi-pencil"></span>';
    editButton.onclick = function () {
        editNote(editedNoteIndex);
    };
}


function deleteNote(noteIndex) {
    // Implement the logic to delete the note, you can use AJAX to communicate with the server
    // Remove the corresponding DOM element if the deletion is successful
    var noteElement = document.getElementById('note' + noteIndex);
    noteElement.parentNode.parentNode.remove();
}


