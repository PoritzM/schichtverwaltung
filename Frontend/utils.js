function showToast(message, type = "success", duration = 2000) {
    const toast = document.getElementById("toast");
    if (!toast) return;

    toast.textContent = message;

    // Alte Klassen entfernen (falls schon error/success drin ist)
    toast.classList.remove("success", "error");

    toast.classList.add("show", type);

    setTimeout(() => {
        toast.classList.remove("show", "success", "error");
    }, duration);
}


function getErrorMessage(statusCode) {
    switch (statusCode) {
        case 403: return "Eintragen ist für dieses Event nicht erlaubt.";
        case 404: return "Eintrag nicht gefunden. Prüfe, ob die ID korrekt ist.";
        case 409: return "Keine Änderung: Der übergebene Wert ist bereits gesetzt.";
        case 500: return "Serverfehler: Bitte später erneut versuchen.";
        default: return "Unbekannter Fehler aufgetreten.";
    }
}


function Start() {

    window.location.href = "Seite.html"

}


function toggleMenu() {
    const menu = document.getElementById('menuList');
    menu.style.display = menu.style.display === 'block' ? 'none' : 'block';


}