#version 150
//Paralysis Fragment


uniform sampler2D DiffuseSampler;
uniform sampler2D BlurSampler; //--

uniform vec4 ColorModulate;
uniform int Multiplier;// just checking ints work
uniform vec2 OutSize;


in vec2 texCoord;

out vec4 fragColor;


void main() {

    //references
    //vec2 trueCoord = gl_FragCoord.xy / OutSize.x;
    //float proportion = OutSize.x / OutSize.y;
    //vec2 mid = vec2(0.5, 0.5 / prop);

    //define norm
    fragColor = texture(DiffuseSampler, texCoord) * ColorModulate * float(Multiplier);
    //blur additive
    //vec4 blurColor = texture(BlurSampler, texCoord) * ColorModulate * float(Multiplier);
    //fragColor.xyz = mix(fragColor.xyz, blurColor.xyz,distance(texCoord,vec2(0.5)));
    //greyscaling calculator
    float gs = (fragColor.x + fragColor.y + fragColor.z)/3;
    //apply greying at edges
    fragColor.xyz = mix(fragColor.xyz,vec3(gs),distance(texCoord,vec2(0.5)));

}


