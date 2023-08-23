import { token } from '@utils/api';

const SERVER_URL = import.meta.env.VITE_SERVER_URL;
const dataMap = new Map();

export function useSuspenseFetch(url: string) {
  if (dataMap.get(url) && dataMap.get(url).status === 'resolved') {
    return dataMap.get(url).dataMap;
  }

  const promise = fetch(`${SERVER_URL}${url}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.accessToken}`,
    },
    credentials: 'include',
  }).then((res) => res.json());

  promise
    .then((res) => {
      setTimeout(() => {
        dataMap.set(url, {
          dataMap: res,
          status: 'resolved',
        });
      }, 100);
    })
    .catch((err) => {
      throw err;
    });

  throw promise;
}
