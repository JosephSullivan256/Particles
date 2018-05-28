#version 330 core
out vec4 FragColor;
in vec2 outPos;

uniform vec2[40] positions;

vec3 hsv2rgb(vec3 c)
{
	vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
	vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
	return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main()
{
	float count = 40.0;
	float inverseSquares = 0.0;
	for(int i = 0; i < count; i++){
		float dx = outPos.x-positions[i].x;
		float dy = outPos.y-positions[i].y;
		float r2 = pow(dx,2)+pow(dy,2.0);
		inverseSquares+=1.0/(200.0*r2+1.0);
	}
	//if(inverseSquares < 1.0) inverseSquares = 0.0;
    //FragColor = vec4(hsv2rgb(vec3(inverseSquares,1.0,1.0)), 1.0);
    FragColor = vec4(vec3(1.0,0.2,0.1)*inverseSquares, 1.0);
}