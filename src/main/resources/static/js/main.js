document.addEventListener('DOMContentLoaded', function () {
    const menuBar = document.querySelector('.menu-bar');
    let isMenuOpen = false;

    menuBar.addEventListener('click', function () {
        if (isMenuOpen) {
            menuBar.style.height = '50px'; // 메뉴를 닫을 때 높이
        } else {
            menuBar.style.height = '100px'; // 메뉴를 열 때 높이
        }
        isMenuOpen = !isMenuOpen;
    });

    // Hexagon click actions
    document.querySelectorAll('.hexagon').forEach(hex => {
        hex.addEventListener('click', function () {
            if (this.id === 'trainer') {
                showPopup();
            } else if (this.id === 'attendance') {
                window.location.href = '/attendance.html';
            } else if (this.id === 'workout') {
                window.location.href = '/workout.html';
            } else {
                alert(this.id + ' 클릭됨');
            }
        });
    });

    function showPopup() {
        var overlay = document.querySelector('.overlay');
        var popup = document.getElementById('popup');
        overlay.style.display = 'block';
        popup.style.display = 'block';
    }

    function hidePopup() {
        var overlay = document.querySelector('.overlay');
        var popup = document.getElementById('popup');
        overlay.style.display = 'none';
        popup.style.display = 'none';
    }

    document.getElementById('popupClose').addEventListener('click', hidePopup);
    document.querySelector('.overlay').addEventListener('click', hidePopup);
});
