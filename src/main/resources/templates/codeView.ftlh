<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
</head>
<body>
    <#list codes as code>
            <p><span id="load_date">${code.date}</span></p>
            <p><pre id="code_snippet">${code.code}</pre></p>
    </#list>
</body>
</html>