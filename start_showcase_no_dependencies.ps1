$Host.UI.RawUI.BackgroundColor = 'Black'
[console]::BackgroundColor = "black"
cd .node-red
..\node\node.exe ..\node\node_modules\npm\bin\npm-cli.js install
$node_red = Start-Process -FilePath ..\node\node.exe -ArgumentList .\node_modules\node-red\red.js,"-u","." -PassThru -NoNewWindow
cd ..
try {
    Start-Sleep 5
    java -jar .\target\anki-battle-0.3.0-SNAPSHOT.jar
} finally {
    Stop-Process $node_red
}