import { LoginUrl } from '.';

const SERVER_URL = import.meta.env.VITE_SERVER_URL;

interface apiInstanceInterface {
  url: string;
  method: 'GET' | 'POST' | 'DELETE';
  bodyData?: string;
}

const getAccessToken = () => sessionStorage.getItem('accessToken');
const getRefreshToken = () => sessionStorage.getItem('refreshToken');

const tokenApiInstance = async ({ url, method }: apiInstanceInterface) => {
  const tokenData = await fetch(`${SERVER_URL}${url}`, {
    method: method,
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${getRefreshToken()}`,
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
      Authorization: `Bearer ${getAccessToken()}`,
    },
    credentials: 'include',
    body: bodyData,
  })
    .then(async (res) => {
      if (res.status === 401) {
        const { accessToken } = await tokenApiInstance({
          url: LoginUrl.REISSUE,
          method: 'GET',
          bodyData: bodyData,
        });
        sessionStorage.setItem('accessToken', accessToken);

        const newRes = (await apiInstance({
          url: url,
          method: method,
          bodyData: bodyData,
        })) as string;

        return newRes;
      } else if (res.status === 400) {
        alert('이메일/비밀번호를 다시 확인해주세요!!');
      } else {
        return res.json();
      }
    })
    .catch((err) => console.dir(err));

  return response;
};
