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
  const SERVER_URL = import.meta.env.VITE_SERVER_URL;
  const res = await fetch(`${SERVER_URL}${url}`, {
    method: method,
    headers: {
      'Content-Type': 'application/json',
    },
    body: bodyData,
  })
    .then((res) => {
      return res.json();
    })
    .catch((err) => console.error(err));

  return res;
};
