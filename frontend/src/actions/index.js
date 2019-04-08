import axios from 'axios';

const ROOT_URL = 'http://localhost:8080';

export const UPLOAD_FILE = 'UPLOAD_FILE';

export function uploadFile(props){

    let formData = new FormData();
    formData.append('name', "file");
    formData.append('file', props.scheduleUpload[0]);

    const request = axios.post(`${ROOT_URL}/uploadFile`,
        formData,
        { headers: { 'content-type': 'multipart/form-data' }});

    return(dispatch) => {
        request.then(({data}) =>{
            dispatch({type: UPLOAD_FILE, payload: data})
        });
    };
}
