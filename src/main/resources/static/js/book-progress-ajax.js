(function ($) {

    "use strict";

    $(document).on('submit', '.ajax-stay-form', function (event) {
        event.preventDefault();

        var action = $(this).attr('action');
        var formData = new FormData(this);

        fetch(action, {
            method: 'POST',
            body: formData,
            credentials: 'same-origin'
        }).then(function (response) {
            if (response.ok || response.redirected) {
                window.location.reload();
            }
        }).catch(function () {
            window.location.reload();
        });
    });

})(jQuery);
