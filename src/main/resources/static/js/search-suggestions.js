(function ($) {

    "use strict";

    var $input = $('#keyword');
    var $dropdown = $('#searchSuggestions');
    var debounceTimer;

    if (!$input.length || !$dropdown.length) {
        return;
    }

    function hideDropdown() {
        $dropdown.empty().hide();
    }

    function renderSuggestions(books) {
        $dropdown.empty();

        if (!books.length) {
            hideDropdown();
            return;
        }

        books.slice(0, 5).forEach(function (book) {
            var authorNames = (book.authorInBooks || []).map(function (author) {
                return author.name;
            }).join(', ');

            var $item = $('<li>').addClass('search-suggestion-item');
            var $link = $('<a>').attr('href', '/bookdetails/' + book.id);
            $link.append($('<span>').addClass('search-suggestion-title').text(book.bookTitle));
            if (authorNames) {
                $link.append($('<span>').addClass('search-suggestion-author').text(authorNames));
            }
            $item.append($link);
            $dropdown.append($item);
        });

        $dropdown.show();
    }

    $input.on('input', function () {
        var keyword = $input.val().trim();

        clearTimeout(debounceTimer);

        if (!keyword) {
            hideDropdown();
            return;
        }

        debounceTimer = setTimeout(function () {
            $.get('/api/books/suggestions', {keyword: keyword})
                .done(renderSuggestions)
                .fail(hideDropdown);
        }, 300);
    });

    $(document).on('click', function (event) {
        if (!$(event.target).closest('.search-wrapper').length) {
            hideDropdown();
        }
    });

    $input.on('keydown', function (event) {
        if (event.key === 'Escape') {
            hideDropdown();
        }
    });

})(jQuery);
