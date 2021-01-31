onload = async function () {
    createContainer();
    createPizzaTable('pizza')

    let pizzaType = 'pizzaType';
    await sendRequest(pizzaType, "fetch", 'GET', function (json) {
        createHead(pizzaType)
        createTable(pizzaType, json);
    });

    let pizzaAddition = 'pizzaAddition';
    await sendRequest(pizzaAddition, "fetch", 'GET', function (json) {
        createHead(pizzaAddition)
        createTable(pizzaAddition, json);
    });
}

let pizza = {
    pizzaType: null,
    pizzaAdditions: [],
    changed: function () {
        if (this.pizzaType) {
            document.getElementById('pizzaTypeCell').textContent = this.pizzaType.name;
        } else {
            document.getElementById('pizzaTypeCell').textContent = 'Not Chosen';
        }
        let tableElement = document.getElementById('pizza');
        let length = tableElement.rows.length;
        for (let i = length - 1; i > 1; i--) {
            tableElement.deleteRow(i)
        }
        let tBody = tableElement.tBodies[0];
        for (const addition of this.pizzaAdditions) {
            let rowElement = tBody.insertRow();
            rowElement.id = addition.id
            rowElement.insertCell();
            let cellElement = rowElement.insertCell();
            cellElement.textContent = addition.name
        }
    },
    update: function () {
        let pizzaTypes = entityMap.get('pizzaType');
        let find = false;
        if (pizzaTypes)
            for (const pizzaType of pizzaTypes) {
                if (this.pizzaType)
                    find = pizzaType.id == this.pizzaType.id;
                if (find) {
                    this.pizzaType.name = pizzaType.name;
                    break;
                }
            }
        if (!find && this.pizzaType != null) {
            this.pizzaType = null;
        }
        let pizzaAdditions = entityMap.get('pizzaAddition');
        if (pizzaAdditions)
            for (const thisAddition of this.pizzaAdditions) {
                find = false;
                for (const pizzaAddition of pizzaAdditions) {
                    find = pizzaAddition.id == thisAddition.id;
                    if (find) {
                        thisAddition.name = pizzaAddition.name
                        break;
                    }
                }
                if (!find) {
                    let pos = this.pizzaAdditions.indexOf(thisAddition);
                    this.pizzaAdditions.splice(pos, 1);
                }
            }
        this.changed();
    }
}

let entityMap = new Map();

function createPizzaTable(tableId) {
    let upperTableId = tableId.charAt(0).toUpperCase() + tableId.slice(1);
    let divElement = document.createElement('div');
    divElement.id = upperTableId;
    let tableElement = document.createElement('table');
    divElement.append(tableElement);
    tableElement.setAttribute('id', tableId);
    let body = tableElement.createTBody();

    let typeRow = body.insertRow();
    let typeCellName = typeRow.insertCell();
    typeCellName.textContent = 'Pizza type:'
    let typeCell = typeRow.insertCell();
    typeCell.id = 'pizzaTypeCell'
    typeCell.textContent = 'Not Chosen'

    let additionsRow = body.insertRow();
    additionsRow.id = 'pizzaAdditionsRow'
    let additionsNameCell = additionsRow.insertCell();
    additionsNameCell.textContent = 'Additions:';

    let bakeCell = additionsRow.insertCell();
    let bakeButton = document.createElement('button');
    bakeButton.textContent = 'Bake pizza'
    bakeButton.id = 'bakeButton' + tableId;
    bakeCell.innerHTML = bakeButton.outerHTML;

    let container = document.getElementById('container');
    container.insertAdjacentElement('afterbegin', divElement);

    document.getElementById(bakeButton.id).onclick = function () {
        sendRequest(tableId, 'create', 'POST', function (json) {
            // recreateTable(tableId);
            pizza.pizzaType = null;
            pizza.pizzaAdditions = [];
            sendRequest(tableId, 'fetch', 'GET', function (json) {
                console.log(json)
            })
        }, pizza);
    }

}

function createTable(tableId, jsonObject) {
    let upperTableId = tableId.charAt(0).toUpperCase() + tableId.slice(1);
    let divElement = document.getElementById('div' + upperTableId);
    let tableElement = document.createElement('table');
    divElement.append(tableElement);
    tableElement.setAttribute('id', tableId);
    let body = tableElement.createTBody();
    for (const jsonObjectElement of jsonObject) {
        createRow(tableId, body, jsonObjectElement);
    }

    let container = document.getElementById('container');
    container.insertAdjacentElement('beforeend', divElement);
    recreateTable(tableId);
}

function createHead(tableId) {
    let upperTableId = tableId.charAt(0).toUpperCase() + tableId.slice(1);
    let inputId = 'new' + upperTableId;

    let divElement = document.createElement('div');
    divElement.id = 'div' + upperTableId
    let buttonElement = document.createElement('button');
    buttonElement.onclick = async function () {
        let obj = {
            name: document.getElementById(inputId).value
        }
        if (obj.name)
            await sendRequest(tableId, "create", 'POST', function (response) {
                recreateTable(tableId);
            }, obj);
    }
    buttonElement.textContent = 'Create'
    divElement.insertAdjacentElement('afterbegin', buttonElement);

    let inputElement = document.createElement('input');
    inputElement.type = 'text';
    inputElement.id = inputId;
    divElement.insertAdjacentElement('afterbegin', inputElement);

    let container = document.getElementById('container');
    container.insertAdjacentElement('beforeend', divElement);
}

function recreateTable(tableId) {
    let promise = sendRequest(tableId, "fetch", 'GET', function (json) {
        entityMap.set(tableId, json);
        let tableElement = document.getElementById(tableId);
        let tBody = tableElement.tBodies[0];
        let toDelete = [];
        for (const rowElement of tableElement.rows) {
            let found = json.find(function (jsonElement) {
                return jsonElement.id == rowElement.id
            })
            if (!found) {
                toDelete.push(rowElement.rowIndex)
            }
        }
        for (const toDeleteElement of toDelete) {
            tableElement.deleteRow(toDeleteElement);
        }
        let filter = json.filter(function (json) {
            let equals = false;
            for (const rowElement of this) {
                equals = rowElement.id == json.id;
                if (equals)
                    return false;
            }
            return true;
        }, tableElement.rows);
        for (const jsonObjectElement of filter) {
            createRow(tableId, tBody, jsonObjectElement);
        }
        pizza.update();
    });
}

async function sendRequest(entity, request, method, callback, value = null) {
    let url = 'http://localhost:8080/' + entity;
    if (request !== "fetch") {
        url += '/' + request;
    }
    let response = await fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: value ? JSON.stringify(value) : null
    })
    if (response.ok) {
        let json = await response.json();
        callback(json);
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

function createRow(tableId, tBody, jsonObject) {
    let rowElement = tBody.insertRow();
    rowElement.setAttribute('id', jsonObject.id);

    let inputCell = rowElement.insertCell();
    let editInputId = 'editInput' + tableId + jsonObject.id;
    inputCell.innerHTML = '<input id="' + editInputId + '" type="text" value="' + jsonObject.name + '">'

    let editCell = rowElement.insertCell();
    let editButton = document.createElement('button');
    editButton.textContent = 'E'
    editButton.id = 'editButton' + tableId + jsonObject.id;
    editCell.innerHTML = editButton.outerHTML;
    document.getElementById(editButton.id).onclick = function () {
        let obj = {
            id: jsonObject.id,
            name: document.getElementById(editInputId).value
        }
        sendRequest(tableId, 'edit', 'PUT', function (json) {
            recreateTable(tableId);
        }, obj);
    }

    let deleteCell = rowElement.insertCell();
    let deleteButton = document.createElement('button');
    deleteButton.textContent = 'D'
    deleteButton.id = 'deleteButton' + tableId + jsonObject.id;
    deleteCell.innerHTML = deleteButton.outerHTML;
    document.getElementById(deleteButton.id).onclick = function () {
        let obj = {
            id: jsonObject.id,
            name: document.getElementById(editInputId).value
        }
        sendRequest(tableId, 'delete', 'DELETE', function (json) {
            recreateTable(tableId);
        }, obj);
    }

    let chooseCell = rowElement.insertCell();
    let chooseButton = document.createElement('button');
    chooseButton.textContent = 'C'
    chooseButton.id = 'chooseButton' + tableId + jsonObject.id;
    chooseCell.innerHTML = chooseButton.outerHTML;
    document.getElementById(chooseButton.id).onclick = function () {
        let obj = {
            id: jsonObject.id,
            name: document.getElementById(editInputId).value
        }
        if (tableId === 'pizzaAddition') {
            let find = pizza.pizzaAdditions.find(function (value) {
                return value.id == this.id
            }, obj)
            if (!find)
                pizza.pizzaAdditions.push(obj);
        } else if (tableId === 'pizzaType') {
            pizza.pizzaType = obj;
        }
        pizza.update();
    }
}

function createContainer() {
    let container = document.getElementById('container');
    if (container == null) {
        container = document.createElement('div');
        container.setAttribute('id', 'container');
    }
    document.body.insertAdjacentElement('beforeend', container);
}