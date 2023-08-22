import { token } from '@utils/api';
import { useEffect } from 'react';
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';

export const OauthRedirect = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams, setSearchParams] = useSearchParams();

  useEffect(() => {
    setSearchParams(location?.search);

    const accessT = searchParams.get('accessToken') || '';
    const refreshT = searchParams.get('refreshToken') || '';

    token.accessToken = accessT;
    localStorage.setItem('refreshToken', refreshT);

    navigate('/my-car/trim');
  }, []);

  return <div>...</div>;
};
