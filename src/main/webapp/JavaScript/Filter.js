document.addEventListener('DOMContentLoaded', function() {
    const radioGroups = {};
    const form = document.querySelector('.sidebar'); // form filter

    document.querySelectorAll('.sidebar input[type=radio]').forEach(radio => {
        const name = radio.name;

        if (!radioGroups[name]) {
            radioGroups[name] = [];
        }
        radioGroups[name].push(radio);

        // Lưu trạng thái lần trước
        radio.previousChecked = radio.checked;

        // Thêm sự kiện click
        radio.addEventListener('click', function() {
            const resetPage = form.querySelector('input[name="page"]');
            if (this.previousChecked) {
                this.checked = false; // bỏ chọn nếu đang checked
            }

            // Cập nhật trạng thái cho nhóm radio này
            radioGroups[name].forEach(r => r.previousChecked = r.checked);

            // Reset page = 1 khi thay đổi filter
            if (resetPage) {
                resetPage.value = 1;
            }

        });
    });
});
