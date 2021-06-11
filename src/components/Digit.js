import * as React from "react";
import { useState, useEffect } from "react";
import * as tf from "@tensorflow/tfjs";
import SignaturePad from "react-signature-pad-wrapper";
import DeleteIcon from '@material-ui/icons/Delete';
import SendIcon from '@material-ui/icons/Send';
import Button from '@material-ui/core/Button';
import Alert from '@material-ui/lab/Alert';
import './Digit.css'
import { div } from "@tensorflow/tfjs";
// https://github.com/hisami/tensorflow-mnist-react/blob/master/package.json origin
// https://github.com/GantMan/ReactNative_MNIST/blob/master/App.js actual
const Digit = () => {
  const [model, setModel] = useState(null);
  const [signaturePad, setSignaturePad] = useState(null);
  const [maxAccuracy, setMaxAccuracy] = useState(null);

  useEffect(() => {
    tf.loadModel(
      "https://raw.githubusercontent.com/tsu-nera/tfjs-mnist-study/master/model/model.json"
    ).then(model => {
      setModel(model);
    });
  }, []);

  const getImageData = () => {
    return new Promise(resolve => {
      const context = document.createElement("canvas").getContext("2d");

      const image = new Image();
      const width = 28;
      const height = 28;

      image.onload = () => {
        context.drawImage(image, 0, 0, width, height);
        const imageData = context.getImageData(0, 0, width, height);

        for (let i = 0; i < imageData.data.length; i += 4) {
          const avg =
            (imageData.data[i] +
              imageData.data[i + 1] +
              imageData.data[i + 2]) /
            3;

          imageData.data[i] = avg;
          imageData.data[i + 1] = avg;
          imageData.data[i + 2] = avg;
        }

        resolve(imageData);
      };

      image.src = signaturePad.toDataURL();
    });
  };

  const getAccuracyScores = imageData => {
    return tf.tidy(() => {
      const channels = 1;
      let input = tf.fromPixels(imageData, channels);
      input = tf.cast(input, "float32").div(tf.scalar(255));
      input = input.expandDims();

      return model.predict(input).dataSync();
    });
  };

  const predict = () => {
    getImageData()
      .then(imageData => getAccuracyScores(imageData))
      .then(accuracyScores => {
        const maxAccuracy = accuracyScores.indexOf(
          Math.max.apply(null, accuracyScores)
        );
        setMaxAccuracy(maxAccuracy);
      });
  };

  const reset = () => {
    signaturePad.instance.clear();
    setSignaturePad(null);
    setMaxAccuracy(null);
  };

  return (
    <div className="container">

      <div >
        <div>
          <SignaturePad
            width={280}
            height={280}
            options={{
              minWidth: 6,
              maxWidth: 6,
              penColor: "white",
              backgroundColor: "black"
            }}
            ref={ref => setSignaturePad(ref)}
          />
        </div>
    
       <Button
        className="button"
        variant="contained"
        color="primary"
        onClick={predict}
        disabled={!(!maxAccuracy)} 
        startIcon={<SendIcon />}
      >
        Send
      </Button>
      <Button
        className="button"
        variant="contained"
        color="secondary"
        onClick={reset}
        startIcon={<DeleteIcon />}
      >
        Delete
      </Button>
     {maxAccuracy && <Alert 
      className="alert"
      variant="filled"
      severity="success">
     Success result: <b>{maxAccuracy}</b>
      </Alert>}

      
      </div>
    </div>
  );
};

export default Digit;