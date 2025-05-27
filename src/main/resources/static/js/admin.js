console.log("admin.js loaded");
document.querySelector("#image_file_input").addEventListener("change", function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.querySelector("#uploaded_image_preview").src = e.target.result;
           
        };
        reader.readAsDataURL(file);
    }
});