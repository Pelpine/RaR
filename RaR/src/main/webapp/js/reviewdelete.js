$(function() {
    // 리뷰 수정
    $(document).on('click', '.update', function() {
        // 댓글 번호
        let re_num = $(this).attr('data-renum');
        // 댓글 내용
        let re_comment_element = $(this).closest('.review').find('p');
        if (re_comment_element.length > 0) {
            let re_comment = re_comment_element.html().replace(/<br>/gi, '\n');

            // 댓글 수정폼 UI
            let modifyUI = '<form id="mre_form">';
            modifyUI += '<input type="hidden" name="re_num" id="re_num" value="' + re_num + '">';
            modifyUI += '<textarea rows="3" cols="50" name="re_comment" id="re_comment" class="rep-content">' + re_comment + '</textarea>';    
            modifyUI += '<div id="mre_second" class="align-right">';
            modifyUI += ' <input type="submit" value="수정">';
            modifyUI += ' <input type="button" value="취소" class="re-reset">';
            modifyUI += '</div>';
            modifyUI += '<hr size="1" noshade width="96%">';
            modifyUI += '</form>';

            // 이전에 이미 수정한 댓글이 있을 경우 수정 버튼을 클릭하면 sub-item 클래스로 지정된 div를 환원시키고 수정폼을 제거
            initModifyForm();

            // 수정 버튼을 감싸고 있는 div 숨기기
            $(this).parent().hide();

            // 수정 폼을 수정하고자 하는 데이터가 있는 div에 노출
            $(this).closest('.review').append(modifyUI);
        } else {
            alert('댓글 내용을 찾을 수 없습니다.');
        }
    });

    // 댓글 수정폼 초기화
    function initModifyForm() {
        $('.sub-item').show();
        $('#mre_form').remove();
    }

    // 수정폼에서 취소 버튼 클릭 시 수정폼 초기화
    $(document).on('click', '.re-reset', function() {
        initModifyForm();
    });

    // 댓글 수정
    $(document).on('submit', '#mre_form', function(event) {
        if ($('#re_comment').val().trim() == '') {
            alert('내용을 입력하세요');
            $('#re_comment').val('').focus();
            return false;
        }

        // 폼에 입력한 데이터 반환
        let form_data = $(this).serialize();

        // 서버와 통신
        $.ajax({
            url: '../review/update.do', // 여기에 URL 오타가 있는지 확인하세요
            type: 'post',
            data: form_data,
            dataType: 'json',
            success: function(param) {
                if (param.result == 'logout') {
                    alert('로그인 해야 수정할 수 있습니다');
                } else if (param.result == 'success') {
                    $('#mre_form').parent().find('p').html($('#re_comment').val().replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\n/g, '<br>'));
                    $('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초 미만');

                    // 수정폼 삭제 및 초기화
                    initModifyForm();
                } else if (param.result == 'pass') {
                    alert('타인의 글을 수정할 수 없습니다.');
                } else {
                    alert('댓글 수정 오류 발생');
                }
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
        });

        // 기본 이벤트 제거
        event.preventDefault();
    });
     // 리뷰 삭제
    $(document).on('click', '.delete', function() {
        // 댓글 번호
        let re_num = $(this).attr('data-renum');

        // 서버와 통신
        $.ajax({
            url: '../review/delete.do', // 여기에 URL 오타가 있는지 확인하세요
            type: 'post',
            data: { re_num: re_num },
            dataType: 'json',
            success: function(param) {
                if (param.result == 'logout') {
                    alert('로그인 해야 삭제할 수 있습니다');
                } else if (param.result == 'success') {
                    alert('삭제 완료');
                    selectList(1); // 리뷰 목록을 다시 로드합니다.
                } else if (param.result == 'pass') {
                    alert('타인의 글을 삭제할 수 없습니다.');
                } else {
                    alert('댓글 삭제 오류 발생');
                }
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
        });
    });
    selectList(1);
});
