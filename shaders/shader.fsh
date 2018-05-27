#version 330 core
out vec4 FragColor;
in vec2 outPos;

uniform vec2[40] positions;

void main()
{
	float count = 40.0;
	float inverseSquares = 0.0;
	for(int i = 0; i < count; i++){
		float dx = outPos.x-positions[i].x;
		float dy = outPos.y-positions[i].y;
		float r2 = pow(dx,2)+pow(dy,2.0);
		inverseSquares+=1.0/(200*r2+1.0);
	}
	//if(inverseSquares < 1) inverseSquares = 0;
    FragColor = vec4(vec3(1,0.2,0.2)*inverseSquares, 1.0);
}