#version 150
//Paralysis Vertex

in vec4 Position;

uniform mat4 ProjMat;
uniform vec2 OutSize;
uniform vec2 InSize;

out vec2 texCoord;
out vec2 outTexel;

void main(){
    vec4 outPos = ProjMat * vec4(Position.xy, 0.0, 1.0);
    gl_Position = vec4(outPos.xy, 0.2, 1.0);
    outTexel = 1.0 / InSize;
    texCoord = Position.xy / OutSize;
}