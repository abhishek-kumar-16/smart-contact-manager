console.log("script.js loaded");

let currentTheme=getTheme();
console.log("current theme: "+currentTheme);

document.addEventListener("DOMContentLoaded", function() {
    // set the theme to light or dark
    toggleTheme();
})



// to toggle between light and dark mode
function toggleTheme() {   
   

//   toggle the theme
    const toggleTheme= document.querySelector("#toggle_theme");
      toggleTheme.querySelector("span").textContent
    = currentTheme=="light" ? "Dark" : "Light";     
  document.querySelector("html").classList.add(currentTheme);
    toggleTheme.addEventListener("click", (event)=> {
        if (currentTheme=="light"){
            currentTheme="dark";
            setTheme(currentTheme);
            document.querySelector("html").classList.remove("light");
            document.querySelector("html").classList.add("dark");
        }
        else{
            currentTheme="light";
            setTheme(currentTheme);
            document.querySelector("html").classList.remove("dark");
            document.querySelector("html").classList.add("light");
        }
      console.log("toggle theme clicked");
      toggleTheme.querySelector("span").textContent
    = currentTheme=="light" ? "Dark" : "Light";
    })

    
}

//  set the theme to light or dark
function setTheme(theme) {  
    localStorage.setItem("theme", theme);
}

// get the theme from local storage
function getTheme() {
    let theme= localStorage.getItem("theme");
    if (theme==null){
        theme="light";
    }
    return theme;
}