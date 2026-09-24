(function ($) {

    "use strict";

    function initBookDescriptionToggle(context) {
        var $context = $(context);
        var $infoBlock = $context.find('#bookInfoBlock');
        var $panel = $context.find('#bookDescriptionPanel');
        var $btn = $context.find('#seeMoreBtn');
        var $label = $context.find('#seeMoreLabel');
        var $icon = $context.find('#seeMoreIcon');
        var $image = $context.find('.main-img img');

        if (!$infoBlock.length || !$panel.length || !$btn.length || !$image.length) {
            return;
        }

        var imageHeight = $image[0].getBoundingClientRect().height;
        var overflow = $infoBlock[0].scrollHeight - imageHeight;

        if (overflow <= 0) {
            return;
        }

        var descriptionHeight = $panel[0].scrollHeight;
        var clippedHeight = Math.max(descriptionHeight - overflow, 20);

        $panel.addClass('clipped').css('max-height', clippedHeight + 'px');
        $btn.show();

        $btn.off('click').on('click', function () {
            var expanded = $panel.toggleClass('expanded').hasClass('expanded');
            $panel.css('max-height', expanded ? 'none' : clippedHeight + 'px');
            $label.text(expanded ? 'See less' : 'See more');
            $icon.toggleClass('fa-chevron-down', !expanded).toggleClass('fa-chevron-up', expanded);
        });
    }

    window.initBookDescriptionToggle = initBookDescriptionToggle;

    $(document).ready(function () {
        initBookDescriptionToggle(document);
    });

})(jQuery);
