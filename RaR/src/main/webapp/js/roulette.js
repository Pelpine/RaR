const $c = document.querySelector("canvas");
const ctx = $c.getContext('2d');

const product = [
  "300", '50', "30", "100", "10", "500", '50', "100", "600",
];

const colors = ["#dc0936", "#e6471d", "#f7a416", "#efe61f ", "#60b236", "#209b6c", "#169ed8", "#3f297e", "#87207b", "#be107f", "#e7167b"];

const newMake = () => {
  const [cw, ch] = [$c.width / 2, $c.height / 2];
  const arc = Math.PI / (product.length / 2);

  for (let i = 0; i < product.length; i++) {
    ctx.beginPath();
    ctx.fillStyle = colors[i % (colors.length - 1)];
    ctx.moveTo(cw, ch);
    ctx.arc(cw, ch, cw, arc * (i - 1), arc * i);
    ctx.fill();
    ctx.closePath();
  }

  ctx.fillStyle = "#fff";
  ctx.font = "18px Pretendard";
  ctx.textAlign = "center";

  for (let i = 0; i < product.length; i++) {
    const angle = (arc * i) + (arc / 2);

    ctx.save();

    ctx.translate(
      cw + Math.cos(angle) * (cw - 50),
      ch + Math.sin(angle) * (ch - 50)
    );

    ctx.rotate(angle + Math.PI / 2);

    product[i].split(" ").forEach((text, j) => {
      ctx.fillText(text, 0, 30 * j);
    });

    ctx.restore();
  }
};

const rotate = () => {
  $c.style.transform = 'initial';
  $c.style.transition = 'initial';

  setTimeout(() => {
    const ran = Math.floor(Math.random() * product.length);
    const arc = 360 / product.length;
    const rotate = (ran * arc) + 3600 + (arc * 3) - (arc / 4);

    $c.style.transform = `rotate(-${rotate}deg)`;
    $c.style.transition = '2s';

    const point = product[ran]; // point 변수에 선택된 제품 할당

    $.ajax({
      url: '../event/rouletteEvent.do',
      data: {"point": point}, // point 변수를 AJAX 호출에 사용
      type: 'post',
      dataType: 'json',
      success: function (param) {
        if (param.result == 'logout') {
          alert('로그인 후 사용하세요');
        } else if (param.result == 'noTicket') {
          alert('티켓이 없습니다.');
        } else if (param.result == 'success') {
          alert(`${point} 당첨!`);
          const ticketCount = document.getElementById('ticketCount');
          ticketCount.innerText -= 1; //어차피 새로 고침하면 새롭게 불러온 티켓 카운트가 표시, 이는 1씩 빼준 값과 동일
        } else {
          alert('룰렛 오류 발생');
        }
      },
      error: function () {
        alert('네트워크 오류 발생');
      }
    });
  }, 1);
};

newMake();
