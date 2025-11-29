<?php
$user = $_POST['user'];
$pass = $_POST['pass'];
if ($user === 'admin' && $pass === '123456') {
    echo "Login successful!";
} else {
    echo "Invalid credentials.";
}
?>