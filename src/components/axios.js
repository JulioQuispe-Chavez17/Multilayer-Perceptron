import axios from 'axios'

const instance = axios.create({
    baseURL: 'https://bigml.io/andromeda'
})

export default instance