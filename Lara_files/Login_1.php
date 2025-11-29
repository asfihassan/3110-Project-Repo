<?php
$user = $_POST['user'];
$pass = $_POST['pass'];
if ($user === 'admin' && $pass === 'password123') {
    echo "Login successful!";
} else {
    echo "Invalid credentials.";
}
?>