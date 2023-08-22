const SERVER_URL = import.meta.env.VITE_SERVER_URL;

let status = 'pending';
let data;
export function useSuspenseFetch(url: string) {
  if (status === 'resolved') {
    return data;
  }

  const promise = fetch(`${SERVER_URL}${url}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTIxNjk5NzAsImV4cCI6MTcyMzcwNTk3MCwiaXNzIjoiVG9wQ2FyaXZpbmciLCJzdWIiOiIxIn0.p1bkF0pLsHkobfdkyPyGBjaClOHDhXbUFpeagBUWvx4`,
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
