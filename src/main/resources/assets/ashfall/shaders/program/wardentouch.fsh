#version 150
//Paralysis Fragment


uniform sampler2D DiffuseSampler;
//uniform sampler2D BlurSampler; //--

uniform vec4 ColorModulate;
uniform int Multiplier;// just checking ints work
uniform vec2 InSize;
uniform vec2 OutSize;
uniform vec3 RedMatrix;
uniform vec3 GreenMatrix;
uniform vec3 BlueMatrix;
uniform float Saturation;



in vec2 oneTexel;
in vec2 texCoord;

out vec4 fragColor;

//UTILS
//-------------------
vec3 rgb2hsv(vec3 intake){
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(intake.bg, K.wz), vec4(intake.gb, K.xy), step(intake.b, intake.g));
    vec4 q = mix(vec4(p.xyw, intake.r), vec4(intake.r, p.yzx), step(p.x, inktake.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}
vec3 hsv2rgb(vec3 outtake){
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(outtake.xxx + K.xyz) * 6.0 - K.www);
    return outtake.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), outtake.y);
}

//-------------------


void main() {
    //define norms
    fragColor = texture(DiffuseSampler, texCoord) * ColorModulate * float(Multiplier);
    vec3 fragHSV = rgb2hsv(fragColor.xyz);
    float hueJackman = fragHSV.x * 360;


    //greyscaling calculator
    vec3 gs = vec3((fragColor.x + fragColor.y + fragColor.z)/3);

    float RedValue = dot(fragColor.rgb, RedMatrix);
    float GreenValue = dot(fragColor.rgb, GreenMatrix);
    float BlueValue = dot(fragColor.rgb, BlueMatrix);
    vec3 OutColor = vec3(RedValue, GreenValue, BlueValue);
    OutColor = (OutColor * 1.0);
    float Luma = dot(OutColor, GreenMatrix);
    vec3 Chroma = OutColor - Luma;
    OutColor = (Chroma * Saturation) + Luma;

    //apply greying at edges

    fragColor.xyz = mix(fragColor.xyz,OutColor, distance(texCoord,vec2(0.5)));
    //fragColor.xyz = gs;
    //}

}


