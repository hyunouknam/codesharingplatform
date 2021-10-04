<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
</head>
<body>
    <#list codes as code>
            <p>${code.date}</p>
            <p><pre>${code.code}</pre></p>
    </#list>
</body>
</html>