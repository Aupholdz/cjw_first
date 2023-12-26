$(".submit").click(function () {

    var email = $(".email").val();
    var firstName = $(".firstName").val();
    var lastName = $(".lastName").val();

    var gender = $(".genderMale").is(":checked")?"M":"F";

    var dateOfBirth = $(".dateOfBirth").val();
    var phone = $(".phone").val();
    var address = $(".address").val();
   
    var photo = $(".photo").prop("src");

    var roleId = $(".roleUser").is(":checked")?"1":"2";

        $.ajax({
            type:"post",
            url:"http://localhost:8080/SunshineAirlines/addUser",
            data:{email,firstName,lastName,gender,dateOfBirth,phone,address,photo,roleId},

            success(msg){
                var json = JSON.parse(msg);
                if (json.flag == "success") {
                    location.href ="UserManagement.html";
                } else {
                    alert(json.data);
                }

            }

        })


    })

    $(".cancel").click(function () {
        location.href = "UserManagement.html";
    })

    $(".upload-input").change(function () {
        var reader =new FileReader();
        reader.readAsDataURL(this.files[0]);

        reader.onload = function () {
            $(".photo").attr("src",reader.result);
        }



    })

