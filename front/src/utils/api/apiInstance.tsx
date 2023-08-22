import { LoginUrl } from '.';
const SERVER_URL = import.meta.env.VITE_SERVER_URL;
interface apiInstanceInterface {
  url: string;
  method: 'GET' | 'POST' | 'DELETE';
  bodyData?: string;
}

export const token = { accessToken: null } as { accessToken: null | string };
const getAccessToken = () => token.accessToken;
const getRefreshToken = () => localStorage.getItem('refreshToken');

let isFirstRequestToken = true;

const tokenApiInstance = async ({ url, method }: apiInstanceInterface) => {
  isFirstRequestToken = false;
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
    .catch((err) => {
      console.error(err);
    });
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
      const firstStatusNumber = String(res.status).split('')[0];
      if (res.status === 404) {
        return 'NOT_FOUND';
      } else if (res.status === 401 && isFirstRequestToken) {
        const { accessToken, refreshToken } = await tokenApiInstance({
          url: LoginUrl.REISSUE,
          method: 'GET',
          bodyData: bodyData,
        });

        // 유효한 토큰이 없으면 로그인화면으로 리다이렉트
        if (!refreshToken || !accessToken) {
          localStorage.clear();
          window.location.href = '/';
          return;
        }

        token.accessToken = accessToken;
        localStorage.setItem('refreshToken', refreshToken);

        const newRes = (await apiInstance({
          url: url,
          method: method,
          bodyData: bodyData,
        })) as string;

        return newRes;
      } else if (res.status === 302) {
        return 'LOGOUT';
      } else if (firstStatusNumber !== '2') {
        return;
      } else {
        isFirstRequestToken = true;
        return res.json();
      }
    })
    .catch((err) => {
      console.error(err);
      throw err;
    });
  return response;
};
