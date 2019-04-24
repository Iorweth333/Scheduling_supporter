import axios from 'axios';

const ROOT_URL = 'http://localhost:8080';

export const UPLOAD_FILE = 'UPLOAD_FILE';
export const FETCH_LESSONS = 'FETCH_LESSONS';

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
export function uploadFile2(props){

    let formData = new FormData();
    formData.append('file', new File([props], 'sheetjs.xlsx'));


    const request = axios.post(`${ROOT_URL}/uploadFile`,
        formData,
        { headers: { 'content-type': 'multipart/form-data' }});

    return(dispatch) => {
        request.then(({data}) =>{
            dispatch({type: UPLOAD_FILE, payload: data})
        });
    };
}

export function fetchLessons(props){

    const request = axios.get(`${ROOT_URL}/schedule`);

    return(dispatch) => {
        request.then(({data}) =>{
            let ret = data.map( lesson =>{
                return [{value: lesson.date},
                        {value: lesson.startsAt},
                        {value: lesson.endsAt},
                        {value: lesson.subject.name},
                        {value: lesson.lecturer.name + " " + lesson.lecturer.surname},
                        {value: lesson.studentsGroup.name}]
            });
            console.log(data);
            dispatch({type: FETCH_LESSONS, payload: ret})
        });
    };
}

