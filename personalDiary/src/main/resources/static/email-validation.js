function validateEmail(inputText){
    var mail = document.forms["registrationForm"]["email"];
    var mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (mail.match(mailformat)){

    }else{
        alert("invalid email address");
        return false;
    }
}