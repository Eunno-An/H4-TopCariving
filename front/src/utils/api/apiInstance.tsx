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
  const accessToken =
    'eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTIxNjk5NzAsImV4cCI6MTcyMzcwNTk3MCwiaXNzIjoiVG9wQ2FyaXZpbmciLCJzdWIiOiIxIn0.p1bkF0pLsHkobfdkyPyGBjaClOHDhXbUFpeagBUWvx4';
  const SERVER_URL = import.meta.env.VITE_SERVER_URL;
  const res = await fetch(`${SERVER_URL}${url}`, {
    method: method,
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    credentials: 'include',
    body: bodyData,
  })
    .then((res) => {
      return res.json();
    })
    .catch((err) => console.error(err));

  return res;
};

export const loginInstance = async ({
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
      return res.headers;
    })
    .catch((err) => console.error(err));

  return res;
};
