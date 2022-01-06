<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
</head>
<body>
    <textarea id = "code_snippet"> // write your code here </textarea><br><br>
    <label for="time_restriction">Time restriction:</label>
    <input id="time_restriction" type="text" value="0"/><br><br>
    <label for="views_restriction">Views restriction</label>
    <input id="views_restriction" type="text" value="0"/><br><br>
    <button id = "send_snippet" type= "submit" onclick="send()">Submit</button>

    <script type="text/javascript">
        function send() {
            let object = {
                "code": document.getElementById("code_snippet").value,
                "time": document.getElementById("time_restriction").value,
                "views": document.getElementById("views_restriction").value
            };

            let json = JSON.stringify(object);

            let xhr = new XMLHttpRequest();
            xhr.open("POST", '/api/code/new', false)
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.send(json);

            if (xhr.status === 200) {
                let response = xhr.responseText;
                let jsonResponse = JSON.parse(response);
                alert("Snippet created! The ID is: " + jsonResponse["id"]);
            }
        }
    </script>
</body>
</html>
