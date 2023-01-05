<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>객체탐치</title>
</head>
<body style="margin: 40px;">
    <h3>객체 탐지 결과</h3>
    <hr>
    <canvas id="tcanvas" width="300" height="300"></canvas>
    <br><br>
    <button onclick="location.href='/detect/naver'">재실행</button>


    <script>
      	let jsonStr = '${jsonResult}';
	    let obj = JSON.parse(jsonStr);
        let prediction = obj.predictions[0];
        let num = parseInt(prediction.num_detections);
        let names = prediction.detection_names;
        let scores = prediction.detection_scores;
        let boxes =  prediction.detection_boxes;
        
        // c:/Temp/yolo-test.jpg의 이미지파일을 그림그리고 위에서 찾은 디텍팅값을 편집해서 박스 쳐주기
        const canvas = document.getElementById('tcanvas');
        let ctx = canvas.getContext("2d");
        let img = new Image();
        img.src =  '/file/download?fileName=${fileName}';
        img.onload = function() {
            canvas.width = img.width;
            canvas.height = img.height;
            ctx.drawImage(img, 0, 0, img.width, img.height);

            ctx.strokeStyle = 'red';
            ctx.linewidth = 2;
            for (let i=0; i<num; i++){
                if (parseFloat(scores[i]) > parseFloat(0.70)){		//정확도
                    let x = boxes[i][1] * img.width;
                    let y = boxes[i][0] * img.height;
                    let w = (boxes[i][3] - boxes[i][1]) * img.width;
                    let h = (boxes[i][2] - boxes[i][0]) * img.height;
                    let label = names[i] + ' (' + parseInt(scores[i] * 100) + '%)';
                    
                    ctx.strokeRect(x, y, w, h);
                    ctx.strokeText(label, x+5, y-5);
                }
            }
        }
        
    </script>
</body>
</html>