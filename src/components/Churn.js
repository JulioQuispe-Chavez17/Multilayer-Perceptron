import React, { useState } from 'react';
import {  Button, FormControl, FormGroup, FormControlLabel, Radio,  RadioGroup, FormLabel} from '@material-ui/core';
import SendIcon from '@material-ui/icons/Send';
import DeleteIcon from '@material-ui/icons/Delete';
import Alert from '@material-ui/lab/Alert';
import { makeStyles } from '@material-ui/core/styles';
import axios from './axios'
import "./Churn.css";


const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  formControl: {
    margin: theme.spacing(3),
  },
}));


const Churn = () => {
  const classes = useStyles();
  const [customerServiceCalls, setCustomerServiceCalls] = useState(null);
  const [totalDayMinutes, setTotalDayMinutes] = useState(0);
  const [totalEveMinutes, setTotalEveMinutes] = useState(0);
  const [internationalPlan, setInternationalPlan] = useState("Yes");
  const [totalIntlCalls, setTotalIntlCalls] = useState(0);
  const [totalNightMinutes, setTotalNightMinutes] = useState(0);
  const [numberVmailMessages, setNumberVmailMessages] = useState(0);
  const [voiceMailPlan, setVoiceMailPlan] = useState("Yes");
  const [predict, setPredict] = useState(null);

  const sendData = async(e) => {

    e.preventDefault();
    axios.post('/prediction?username=julioquispe&api_key=45144bb86790abe56fc4b3a66ca1767bd5581b78', {
      "model": "deepnet/60b542b75e269e0554002475",
      "input_data":{     
          "Customer service calls": customerServiceCalls,
          "International plan": internationalPlan,
          "Voice mail plan": voiceMailPlan,
          "Total day minutes": totalDayMinutes,
          "Total eve minutes": totalEveMinutes,       
          "Total intl calls": totalIntlCalls,
          "Total night minutes": totalNightMinutes,
          "Number vmail messages": numberVmailMessages
          
      }
    }).then((response) => {
      isChurn(response.data)
      
});
   
  };

  const cleanData = () =>{
    setCustomerServiceCalls(null)
    setTotalDayMinutes(0)
    setTotalEveMinutes(0)
    setInternationalPlan("Yes")
    setTotalIntlCalls(0)
    setTotalNightMinutes(0)
    setNumberVmailMessages(0)
    setVoiceMailPlan("Yes")
    setPredict(null)
    
  }

  const isChurn = (confidence) => {
    setPredict(confidence.output);
  }

  return(
    
    <div class="field">
     <form className="app__form">
       <FormControl className={classes.formControl}>
         <FormGroup>
         <FormLabel component="legend">Customer service calls</FormLabel>
        <input className="spinner" type="number" min={0} max={11}  onkeypress="return false" placeholder="Enter a customer service calls" value={customerServiceCalls} onChange={event => setCustomerServiceCalls(event.target.value)}/>     
        <FormLabel component="legend">Total day minutes</FormLabel> 
        <input className="spinner" type="number" min={0} max={4385}  onkeypress="return false" placeholder="Enter a total day minutes" value={totalDayMinutes} onChange={event => setTotalDayMinutes(event.target.value)}/>     
        <FormLabel component="legend">Total eve minutes</FormLabel>  
        <input className="spinner" type="number" min={0} max={4546}  onkeypress="return false" placeholder="Enter a total eve minutes" value={totalEveMinutes} onChange={event => setTotalEveMinutes(event.target.value)}/>     
        <FormLabel component="legend">International Plan</FormLabel>
        <RadioGroup  value={internationalPlan} defaultValue="Yes" onChange={event => setInternationalPlan(event.target.value)}>
          <FormControlLabel value="Yes" control={<Radio />} label="Yes" />
          <FormControlLabel value="No" control={<Radio />} label="No" />   
        </RadioGroup>
         </FormGroup>
       </FormControl>

       <FormControl className={classes.formControl}>
         <FormGroup>
       
         <FormLabel component="legend">Total intl calls</FormLabel>
        <input className="spinner" type="number" min={0} max={25}  onkeypress="return false" placeholder="Enter a total intl minutes" value={totalIntlCalls} onChange={event => setTotalIntlCalls(event.target.value)}/>       
        <FormLabel component="legend">Total night minutes</FormLabel>
        <input className="spinner" type="number" min={0} max={4609}  onkeypress="return false" placeholder="Enter a total night minutes" value={totalNightMinutes} onChange={event => setTotalNightMinutes(event.target.value)}/>       
        <FormLabel component="legend">Number vmail messages</FormLabel>
        <input className="spinner" type="number" min={0} max={25}  onkeypress="return false" placeholder="Enter a number vmail messages" value={numberVmailMessages} onChange={event => setNumberVmailMessages(event.target.value)}/>   
        <FormLabel component="legend">Voice Mail Plan</FormLabel>
        <RadioGroup value={voiceMailPlan} onChange={event => setVoiceMailPlan(event.target.value)}>
          <FormControlLabel value="Yes" control={<Radio />} label="Yes" />
          <FormControlLabel value="No" control={<Radio />} label="No" />   
        </RadioGroup> 
        
         </FormGroup>
       </FormControl>
       <FormControl>
       <Button
        className="button"
        variant="contained"
        color="primary"
        onClick={sendData}
        disabled={!customerServiceCalls} 
        startIcon={<SendIcon />}
      >
        Send
      </Button>
      <br/>
      <Button
        className="button"
        variant="contained"
        color="secondary"
        onClick={cleanData}
        startIcon={<DeleteIcon />}
      >
        Delete
      </Button>
       </FormControl>
       
  
       {predict && <Alert 
      className="alert"
      variant="filled"
      severity="success">
     El cliente renueva nuestro servicio: <b>{predict}</b>
      </Alert>}
      
  
     </form>
    </div>
    )
}




export default Churn