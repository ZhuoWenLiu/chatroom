// 登录后才能使用（假设我们已经登录了）

// 判断用户是完成了登录
function checkLogin() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/get-current-user.json");
    xhr.onload = function () {
        var r = JSON.parse(xhr.responseText);
        if (r.currentUser) {
            main(r.currentUser);
        } else {
            alert("必须登录后才能使用");
            window.location = "/login.html";
        }
    }
    xhr.send();
}

checkLogin();

function main(currentUser) {

    var ol = document.querySelector("#聊天内容区");
    var textarea = document.querySelector("#输入框");
    var sendBtn = document.querySelector("#发送按钮");

    var ws = new WebSocket("ws://127.0.0.1:8080/message");
    ws.onmessage = function (e) {
        var message = JSON.parse(e.data);
        var liHTML = `<li>
            <div class="聊天头像"><img src="https://img01.sogoucdn.com/app/a/100520093/8379901cc65ba509-45c21ceb904429fc-7f23efc08ddbc10018b13ac470428e84.jpg"></div>
            <div class="聊天内容右部分">
                <div class="聊天昵称">${message.nickname}</div>
                <div class="聊天内容">${message.content}</div>
            </div>
        </li>`;
        ol.innerHTML += liHTML;
    }

    sendBtn.onclick = function () {
        var content = textarea.value;
        textarea.value = '';
        ws.send(content);
    }
    textarea.onkeydown = function (e) {
        if (e.keyCode == 13 && e.ctrlKey) {

            var content = textarea.value;
            textarea.value = '';
            ws.send(content);
        }
    }

    var 群成员总计 = document.querySelector("#群成员总计");
    var 群成员名单 = document.querySelector("#群成员名单 ol");
    function updateUserList() {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/api/user-list.json");
        xhr.onload = function () {
            var result = JSON.parse(xhr.responseText);
            群成员总计.innerText = `群成员 ${result.onlineCount} / ${result.totalCount}`;
            群成员名单.innerHTML = '';
            for (var i in result.userList) {
                var user = result.userList[i];
                var html;
                if (user.online) {
                    html = `<li>
                                 <div class="群成员头像">
                                     <img src="http://img.wxcha.com/m00/98/e2/1defb6b4d898a94b8832287e503a0153.jpg">
                                 </div>
                                 <div class="群成员昵称">${user.nickname}</div>
                             </li>`;
                } else {
                    html = `<li class="不在线">
                               <div class="群成员头像">
                                   <img src="http://img.wxcha.com/m00/98/e2/1defb6b4d898a94b8832287e503a0153.jpg">
                               </div>
                               <div class="群成员昵称">${user.nickname}</div>
                           </li>`
                }

                群成员名单.innerHTML += html;
            }
        }
        xhr.send()
    }

    updateUserList();
    setInterval(updateUserList, 1000);
}