(function ($) {

    "use strict";

    $(document).on('show.bs.modal', '.preview-book-modal', function () {
        var $modal = $(this);
        var $body = $modal.find('.preview-book-body');
        var bookId = $modal.data('book-id');

        $body.html('<p class="text-muted">Loading preview...</p>');

        $.get('/bookdetails/' + bookId, {preview: true})
            .done(function (html) {
                $body.html(html);
                if (window.initBookDescriptionToggle) {
                    window.initBookDescriptionToggle($body[0]);
                }
            })
            .fail(function () {
                $body.html('<p class="text-danger">Could not load preview.</p>');
            });
    });

})(jQuery);
