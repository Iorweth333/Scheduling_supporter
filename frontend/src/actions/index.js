import axios from 'axios';

const ROOT_URL = 'http://localhost:8080';

export const UPLOAD_FILE = 'UPLOAD_FILE';
export const FETCH_LESSONS = 'FETCH_LESSONS';


export function uploadFile(props, history){

    let formData = new FormData();
    formData.append('file', new File([props], 'sheetjs.xlsx'));

    const request = axios.post(`${ROOT_URL}/schedule/file`,
        formData,
        { headers: { 'content-type': 'multipart/form-data' }});

    return(dispatch) => {
        request.then(({data}) =>{
            dispatch(fetchLessons())
            history.push('/lessons');
        });
    };
}

export function fetchLessons(){

    const request = axios.get(`${ROOT_URL}/schedule`);

    return(dispatch) => {
        request.then(({data}) =>{
            dispatch({type: FETCH_LESSONS, payload: data});
        });
    };
}

export function redirectToUpload() {

    const request = axios.get(`${ROOT_URL}/upload`);

    /*return(dispatch) => {
        request.then();
    }*/
}

