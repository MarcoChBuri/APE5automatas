export type AutomatonId = 'tabla1' | 'tabla2' | 'tabla3';

export interface AutomatonDefinition {
	id: AutomatonId;
	label: string;
	description: string;
	alphabet: string;
	endpoint: string;
	sample: string;
	accent: 'amber' | 'cyan' | 'violet';
}

export interface ValidationOutcome {
	accepted: boolean;
	message: string;
	statusCode: number;
	chain: string;
	automaton: AutomatonDefinition;
}
