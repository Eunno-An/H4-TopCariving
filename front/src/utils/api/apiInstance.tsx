export const SERVER_URL = 'http://dev.topcariving.com:8080';

interface apiInstanceInterface {
  url: string;
  method: 'GET' | 'POST' | 'DELETE';
}

export const apiInstance = async ({ url, method }: apiInstanceInterface) => {
  const res = await fetch(`${SERVER_URL}${url}`, { method: method })
    .then((res) => {
      return res.json();
    })
    .catch((err) => console.error(err));

  return res;
};
