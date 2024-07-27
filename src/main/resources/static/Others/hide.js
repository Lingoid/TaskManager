function viewDiv() {
    let createTaskArea = document.getElementById("createTaskArea");
    let toggleButton = document.getElementById("nameChanger");
    if (createTaskArea.style.display === "none" || createTaskArea.style.display === "") {
        createTaskArea.style.display = "block";
        toggleButton.value = "Свернуть";
    } else {
        createTaskArea.style.display = "none";
        toggleButton.value = "Создать задачу";
    }
}

function editTask(desc,name,id) {

    document.getElementById('editTaskName').setAttribute("value", name);
    document.getElementById('editTaskDescription').textContent = desc
    document.getElementById('editTaskId').setAttribute("value", id)

    document.getElementById('editTaskForm').style.display = 'block';


}

function hideEditForm() {

    document.getElementById('editTaskForm').style.display = 'none';
}

let taskTimers = {};

function start(taskId) {
    if (!taskTimers[taskId]) {
        taskTimers[taskId] = {
            timer: null,
            startTime: 0,
            elapsedTime: 0,
            isRunning: false
        };
    }

    if (!taskTimers[taskId].isRunning) {
        taskTimers[taskId].startTime = Date.now() - taskTimers[taskId].elapsedTime;
        taskTimers[taskId].timer = setInterval(() => update(taskId), 10);
        taskTimers[taskId].isRunning = true;
    }
}

function stop(taskId) {
    if (taskTimers[taskId] && taskTimers[taskId].isRunning) {
        clearInterval(taskTimers[taskId].timer);
        taskTimers[taskId].elapsedTime = Date.now() - taskTimers[taskId].startTime;
        taskTimers[taskId].isRunning = false;
    }
}

function reset(taskId) {
    if (taskTimers[taskId]) {
        clearInterval(taskTimers[taskId].timer);
        taskTimers[taskId].startTime = 0;
        taskTimers[taskId].elapsedTime = 0;
        taskTimers[taskId].isRunning = false;
        const timeElement = document.querySelector(`#time_[data-taskId='${taskId}']`);
        if (timeElement) {
            timeElement.textContent = `00:00:00:00`;
        } else {
            console.error(`Элемент времени для задачи с идентификатором ${taskId} не найден.`);
        }
    }
}

function update(taskId) {
    if (taskTimers[taskId]) {
        const curTime = Date.now();
        taskTimers[taskId].elapsedTime = curTime - taskTimers[taskId].startTime;

        let hours = Math.floor(taskTimers[taskId].elapsedTime / (1000 * 60 * 60));
        let minutes = Math.floor(taskTimers[taskId].elapsedTime / (1000 * 60) % 60);
        let seconds = Math.floor(taskTimers[taskId].elapsedTime / 1000 % 60);
        let milliseconds = Math.floor(taskTimers[taskId].elapsedTime % 1000 / 10);

        hours = String(hours).padStart(2,"0");
        minutes = String(minutes).padStart(2,"0");
        seconds = String(seconds).padStart(2,"0");
        milliseconds = String(milliseconds).padStart(2,"0");

        const timeElement = document.querySelector(`#time_[data-taskId='${taskId}']`);
        if (timeElement) {
            timeElement.textContent = `${hours}:${minutes}:${seconds}:${milliseconds}`;
        } else {
            console.error(`Элемент времени для задачи с идентификатором ${taskId} не найден.`);
        }
    }
}

window.onload = function() {
    // Получаем все элементы времени и запускаем для них таймеры
    const timeElements = document.querySelectorAll('.time');
    timeElements.forEach(function(timeElement) {
        const taskId = timeElement.getAttribute('data-taskId');
        start(taskId);
    });
};







