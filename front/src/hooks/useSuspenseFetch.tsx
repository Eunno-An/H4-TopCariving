import { token } from '@utils/api';

const SERVER_URL = import.meta.env.VITE_SERVER_URL;

let status = 'pending';
let data;
export function useSuspenseFetch(url: string) {
  if (status === 'resolved') {
    status = 'pending';
    return data;
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
      status = 'resolved';
      data = res;
    })
    .catch((err) => {
      throw new Error('ERROR');
    });

  throw promise;
}
