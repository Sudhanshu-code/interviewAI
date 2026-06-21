import { useLoginMutation } from "@/services/authApi";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

function LoginPage() {
  const navigate = useNavigate();

  const [login, { isLoading }] = useLoginMutation();

  const { register, handleSubmit } = useForm();

  const onSubmit = async (data) => {
    try {
      await login(data).unwrap();

      navigate("/dashboard");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <div>
        <label>Email</label>

        <input type="email" {...register("email")} />
      </div>

      <div>
        <label>Password</label>

        <input type="password" {...register("password")} />
      </div>

      <button type="submit" disabled={isLoading}>
        Login
      </button>
    </form>
  );
}

export default LoginPage;
