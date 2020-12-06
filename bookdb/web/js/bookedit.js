/**
 * view-controller for bookedit.html
 *
 * M133: Bookshelf
 *
 * @author  Marcel Suter
 * @since   2019-03-19
 * @version 1.0
 */

/**
 * load the book data
 */
$(document).ready(function () {
    showMessage("empty", " ");
    loadPublishers().done(loadBook());

    /**
     * listener for submitting the form
     */
    $("#bookeditForm").submit(saveBook);

    /**
     * listener for button [abbrechen], redirects to bookshelf
     */
    $("#cancel").click(function () {
        window.location.href = "./bookshelf.html";
    });
});

/**
 * loads all publishers
 */
function loadPublishers() {
    var waiting = $.Deferred();
    $
        .ajax({
            url: "./resource/publisher/list",
            dataType: "json",
            type: "GET"
        })
        .done(addPublishers)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                showMessage("error","Kein Verlag gefunden");
            } else {
                window.location.href = "./bookshelf.html";
            }
        })

    return waiting;
}

/**
 *  loads the data of this book
 *
 */
function loadBook() {
    var bookUUID = $.urlParam('uuid');
    if (bookUUID !== null && bookUUID != -1) {
        $
            .ajax({
                url: "./resource/bookshelf/read?uuid=" + bookUUID,
                dataType: "json",
                type: "GET"
            })
            .done(showBook)
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status == 404) {
                    showMessage("error", "Kein Buch gefunden");
                } else {
                    window.location.href = "./bookshelf.html";
                }
            })
    }
}

/**
 * adds all publishers to dropdown
 * @param publisherList
 */
function addPublishers(publisherList) {
    $.each(publisherList, function (index, publisher) {
        $("#publisherUUID").append(new Option(publisher.publisher, publisher.publisherUUID));
    });
}

/**
 * shows the data of this book
 * @param  book  the book data to be shown
 */
function showBook(book) {
    $("#message").empty();
    $("#title").val(book.title);
    $("#author").val(book.author);
    $("#price").val(book.price);
    $("#isbn").val(book.isbn);
    $("#publisherUUID")
        .val(book.publisher.publisherUUID)
        .change();
}

/**
 * sends the book data to the webservice
 * @param form the form being submitted
 */
function saveBook(form) {
    form.preventDefault();
    var uuid="";
    if ($.urlParam('uuid') !== null) {
        uuid = "?uuid=" + $.urlParam('uuid');
    }
    $
        .ajax({
            url: "./resource/bookshelf/save" + uuid,
            dataType: "text",
            type: "POST",
            data: $("#bookeditForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "./bookshelf.html";
        })
        .fail(function (xhr, status, errorThrown) {
            showMessage("error", xhr.responseText);
        })
}