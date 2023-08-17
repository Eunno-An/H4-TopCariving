import { LoginUrl } from '.';

const SERVER_URL = import.meta.env.VITE_SERVER_URL;

export const token = {
  accessToken: '',
  refreshToken: '',
};

interface apiInstanceInterface {
  url: string;
  method: 'GET' | 'POST' | 'DELETE';
  bodyData?: string;
}

const getAccessToken = async ({ url, method }: apiInstanceInterface) => {
  const tokenData = await fetch(`${SERVER_URL}${url}`, {
    method: method,
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.refreshToken}`,
    },
    credentials: 'include',
  })
    .then((res) => {
      return res.json();
    })
    .catch((err) => console.error(err));
  return tokenData;
};

export const apiInstance = async ({
  url,
  method,
  bodyData,
}: apiInstanceInterface) => {
  const response = await fetch(`${SERVER_URL}${url}`, {
    method: method,
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token.accessToken}`,
    },
    credentials: 'include',
    body: bodyData,
  })
    .then(async (res) => {
      // 현재 500인데 401도 수정해야함. 왜냐하면 이것은 어세스 토큰이 만료됬을때 리프레시 토큰을 사용해 다시 어세스 토큰을 받아오는 과정이기 때문.
      if (res.status === 401) {
        const { accessToken } = await getAccessToken({
          url: LoginUrl.REISSUE,
          method: 'GET',
          bodyData: bodyData,
        });
        token.accessToken = accessToken;

        const newRes = (await apiInstance({
          url: url,
          method: method,
          bodyData: bodyData,
        })) as string;

        return newRes;
      } else if (res.status === 400) {
        alert('400 ERROR');
      } else {
        return res.json();
      }
    })
    .catch((err) => console.dir(err));

  return response;
};
