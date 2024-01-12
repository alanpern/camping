// reservation

function reserver(buttonElement) {
    var siteId = buttonElement.getAttribute('data-site-id');
    if (confirm("Voulez-vous réserver ce site ?")) {
        window.location.href = '/reservation?siteId=' + siteId;
    }
}

/*function reserver(buttonElement) {
    var siteId = buttonElement.getAttribute('data-site-id');
    var confirmation = confirm("Voulez-vous réserver ce site ?");
    if (confirmation) {
        window.location.href = '/reservation?siteId=' + siteId;
    }
}*/


/*function confirmerReservation(siteId) {
    var confirmation = confirm("Voulez-vous réserver ce site ?");
    if (confirmation) {
        window.location.href = '/reservation?siteId=' + siteId;
    }
    // Si 'Non' est choisi, rien ne se passe
}*/
