export const SERVER_URL = 'http://dev.topcariving.com:8080';

interface apiInstanceInterface {
  url: string;
  method: 'GET' | 'POST' | 'DELETE';
  bodyData?: string;
}

export const apiInstance = async ({
  url,
  method,
  bodyData,
}: apiInstanceInterface) => {
  const res = await fetch(`${SERVER_URL}${url}`, {
    method: method,
    headers: {
      'Content-Type': 'application/json', // JSON 형태로 데이터를 보낼 것임을 명시
    },
    body: bodyData,
  })
    .then((res) => {
      return res.json();
    })
    .catch((err) => console.error(err));

  return res;
};
